package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;

import java.util.HashMap;
import java.util.Map;

public class MHMods_IntegratedArmor extends BaseHullMod {

    private final float MinArmor = 0.1f;
    private final Map<HullSize, Integer> maxminarmor = new HashMap<>();
    {
        maxminarmor.put(HullSize.FRIGATE, 50);
        maxminarmor.put(HullSize.DESTROYER, 100);
        maxminarmor.put(HullSize.CRUISER, 150);
        maxminarmor.put(HullSize.CAPITAL_SHIP, 200);
    }

    @Override
    public void applyEffectsAfterShipCreation(ShipAPI ship, String id) {
        super.applyEffectsAfterShipCreation(ship, id);
        if (ship.getArmorGrid().getArmorRating() * MinArmor > maxminarmor.get(ship.getHullSize())){
            ship.getMutableStats().getMinArmorFraction().modifyFlat(id,maxminarmor.get(ship.getHullSize()) / ship.getArmorGrid().getArmorRating());
       }else {
            ship.getMutableStats().getMinArmorFraction().modifyFlat(id,MinArmor);
        }
    }

    public String getDescriptionParam(int index, HullSize hullSize) {
	    if (index == 0) return (Math.round(100 * MinArmor) + "%");
        if (index == 1) return "" + maxminarmor.get(HullSize.FRIGATE);
        if (index == 2) return "" + maxminarmor.get(HullSize.DESTROYER);
        if (index == 3) return "" + maxminarmor.get(HullSize.CRUISER);
        if (index == 4) return "" + maxminarmor.get(HullSize.CAPITAL_SHIP);
        return null;
    }


}