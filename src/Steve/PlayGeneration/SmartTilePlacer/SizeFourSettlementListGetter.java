package Steve.PlayGeneration.SmartTilePlacer;

import Settlements.Creation.Settlement;

import java.util.LinkedList;
import java.util.List;

public class SizeFourSettlementListGetter {

    public List<Settlement> getSettlementsOfSizeFourOnly(List<Settlement> s) {
        List<Settlement> ofSizeFour = new LinkedList<>();

        for(Settlement set : s) {
            if(set.getNumberOfHexesInSettlement() == 4)
                ofSizeFour.add(set);
        }

        return ofSizeFour;
    }
}
