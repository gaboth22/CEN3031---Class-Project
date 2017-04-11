package Steve.PlayGeneration;

import Settlements.Creation.Settlement;

import java.util.List;

public class SortSettlementArrayHelper {

    public static Settlement[] convertListToSettlementAndSort(List<Settlement> settlements) {
        Settlement[] settlementArray = settlements.toArray(new Settlement[settlements.size()]);
        return sortSettlementArray(settlementArray);
    }

    private static Settlement[] sortSettlementArray(Settlement[] settlementArray) {
        for(int i = 1; i < settlementArray.length; i++) {
            for(int j = i; j >= 1; j--) {
                if(settlementArray[j].getNumberOfHexesInSettlement() > settlementArray[j-1].getNumberOfHexesInSettlement()) {
                    Settlement temp = settlementArray[j];
                    settlementArray[j] = settlementArray[j-1];
                    settlementArray[j-1] = temp;
                }
            }
        }

        return settlementArray;
    }
}