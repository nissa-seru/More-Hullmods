package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;

import java.util.HashMap;
import java.util.Map;

public class MHMods_IntegratedArmor extends BaseHullMod {

    private final float MinArmor = 0.05f;

    private final Map<HullSize, Integer> maxArmour = new HashMap<>();

    {
        maxArmour.put(HullSize.FIGHTER, 250);
        maxArmour.put(HullSize.FRIGATE, 500);
        maxArmour.put(HullSize.DESTROYER, 1000);
        maxArmour.put(HullSize.CRUISER, 1500);
        maxArmour.put(HullSize.CAPITAL_SHIP, 2000);
    }

    @Override
    public void applyEffectsAfterShipCreation(ShipAPI ship, String id) {
        super.applyEffectsAfterShipCreation(ship, id);
        if (ship.getArmorGrid().getArmorRating() > maxArmour.get(ship.getHullSize())) {
            ship.getMutableStats().getMinArmorFraction().modifyFlat(id, MinArmor * (maxArmour.get(ship.getHullSize()) / ship.getArmorGrid().getArmorRating()));
        } else {
            ship.getMutableStats().getMinArmorFraction().modifyFlat(id, MinArmor);
        }
    }

    public String getDescriptionParam(int index, HullSize hullSize) {
        if (index == 0) return (Math.round(100 * MinArmor) + "%");
        if (index == 1) return "" + Math.round(maxArmour.get(HullSize.FRIGATE) * MinArmor);
        if (index == 2) return "" + Math.round(maxArmour.get(HullSize.DESTROYER) * MinArmor);
        if (index == 3) return "" + Math.round(maxArmour.get(HullSize.CRUISER) * MinArmor);
        if (index == 4) return "" + Math.round(maxArmour.get(HullSize.CAPITAL_SHIP) * MinArmor);
        return null;
    }


}