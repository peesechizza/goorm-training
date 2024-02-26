package com.goorm.tricountsonic.service;

import static java.util.stream.Collectors.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.goorm.tricountsonic.dto.BalanceResult;
import com.goorm.tricountsonic.dto.ExpenseResult;
import com.goorm.tricountsonic.model.Member;
import com.goorm.tricountsonic.model.Settlement;
import com.goorm.tricountsonic.repository.ExpenseRepository;
import com.goorm.tricountsonic.repository.SettlementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SettlementService {
    private final SettlementRepository settlementRepository;
    private final ExpenseRepository expenseRepository;

    @Transactional
  public Settlement createAndJoinSettlement(String settlementName, Member member) {
    Settlement settlement = settlementRepository.create(settlementName);
    settlementRepository.addParticipantToSettlement(settlement.getId(), member.getId());
    settlement.getParticipants().add(member);
    return settlement;
  }

  public void joinSettlement(Long settlementId, Long memberId) {
      //TODO 없는 settle id, member id 요청했을때 예외처리
    settlementRepository.addParticipantToSettlement(settlementId, memberId);
  }

  public List<BalanceResult> getBalanceResult(Long settlmentId) {
    Map<Member, List<ExpenseResult>> collected = expenseRepository.findExpensesWithMemberBySettlementId(settlmentId)
      .stream()
      .collect(groupingBy(ExpenseResult::getPayerMember));

    if (CollectionUtils.isEmpty(collected)) {
      throw new RuntimeException("정산할 정보가 없습니다.");
    }

    Map<Member, BigDecimal> memberAmountSumMap = collected.entrySet().stream()
      .collect(toMap(Map.Entry::getKey, memberListEntry ->
        memberListEntry.getValue().stream().map(ExpenseResult::getAmount)
          .reduce(BigDecimal.ZERO, BigDecimal::add)
      ));

    BigDecimal sumAmount = memberAmountSumMap.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);

    BigDecimal averageAmount = sumAmount.divide(BigDecimal.valueOf(memberAmountSumMap.size()), BigDecimal.ROUND_UP);

    Map<Member, BigDecimal> calculatedAmountMap = memberAmountSumMap.entrySet().stream()
      .collect(toMap(Map.Entry::getKey, memberBigDecimalEntry ->
        memberBigDecimalEntry.getValue().subtract(averageAmount)));


    List<Map.Entry<Member, BigDecimal>> receiver = calculatedAmountMap.entrySet().stream()
      .filter(memberBigDecimalEntry -> memberBigDecimalEntry.getValue().signum() > 0)
      .sorted((o1, o2) -> o2.getValue().subtract(o1.getValue()).signum())
      .collect(toList());

    List<Map.Entry<Member, BigDecimal>> sender = calculatedAmountMap.entrySet().stream()
      .filter(memberBigDecimalEntry -> memberBigDecimalEntry.getValue().signum() < 0)
      .sorted((o1, o2) -> o1.getValue().subtract(o2.getValue()).signum())
      .collect(toList());

    List<BalanceResult> balanceResults = new ArrayList<>();
    int receiverIndex = 0;
    int senderIndex = 0;


    while (receiverIndex < receiver.size() && senderIndex < sender.size()) {
      BigDecimal amountToTransfer = receiver.get(receiverIndex).getValue()
        .add(sender.get(senderIndex).getValue());

      if(amountToTransfer.signum() < 0) {
        balanceResults.add(new BalanceResult(
          sender.get(senderIndex).getKey().getId(),
          sender.get(senderIndex).getKey().getName(),
          receiver.get(receiverIndex).getValue().abs(),
          receiver.get(receiverIndex).getKey().getId(),
          receiver.get(receiverIndex).getKey().getName()
        ));
        receiver.get(receiverIndex).setValue(BigDecimal.ZERO);
        sender.get(senderIndex).setValue(amountToTransfer);
        receiverIndex++;
      } else if(amountToTransfer.signum() > 0) {
        balanceResults.add(new BalanceResult(
          sender.get(senderIndex).getKey().getId(),
          sender.get(senderIndex).getKey().getName(),
          sender.get(senderIndex).getValue().abs(),
          receiver.get(receiverIndex).getKey().getId(),
          receiver.get(receiverIndex).getKey().getName()
        ));
        receiver.get(receiverIndex).setValue(amountToTransfer);
        sender.get(senderIndex).setValue(BigDecimal.ZERO);
        senderIndex++;
      } else {//평균값만큼 낸 경우
        balanceResults.add(new BalanceResult(
          sender.get(senderIndex).getKey().getId(),
          sender.get(senderIndex).getKey().getName(),
          sender.get(senderIndex).getValue().abs(),
          receiver.get(receiverIndex).getKey().getId(),
          receiver.get(receiverIndex).getKey().getName()
        ));
        receiver.get(receiverIndex).setValue(BigDecimal.ZERO);
        sender.get(senderIndex).setValue(BigDecimal.ZERO);
        receiverIndex++;
        senderIndex++;
      }
    }


    return balanceResults;
  }

}
