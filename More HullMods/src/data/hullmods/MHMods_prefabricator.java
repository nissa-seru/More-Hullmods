package data.hullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.combat.WeaponAPI;
import com.fs.starfarer.api.combat.WeaponAPI.WeaponType;
import com.fs.starfarer.api.impl.campaign.ids.Stats;

import java.util.HashMap;
import java.util.Map;

public class MHMods_prefabricator extends BaseHullMod {

    private final Map<HullSize, Integer> fabricationTime = new HashMap<>();

    {
        fabricationTime.put(HullSize.FIGHTER, 120);
        fabricationTime.put(HullSize.FRIGATE, 120);
        fabricationTime.put(HullSize.DESTROYER, 210);
        fabricationTime.put(HullSize.CRUISER, 270);
        fabricationTime.put(HullSize.CAPITAL_SHIP, 420);
    }

    @Override
    public String getDescriptionParam(int index, HullSize hullSize) {
        if (index == 0) return "" + fabricationTime.get(HullSize.FRIGATE);
        if (index == 1) return "" + fabricationTime.get(HullSize.DESTROYER);
        if (index == 2) return "" + fabricationTime.get(HullSize.CRUISER);
        if (index == 3) return "" + fabricationTime.get(HullSize.CAPITAL_SHIP);
        return null;
    }

    @Override
    public void advanceInCombat(ShipAPI ship, float amount) {
        if (!ship.isAlive()) return;
        if (ship.getFullTimeDeployed() >= 0.5f) return;

        Map<String, Object> customCombatData = Global.getCombatEngine().getCustomData();
        String id = ship.getId();

        if (customCombatData.get("MHMods_prefabricator" + id) instanceof Boolean) return;
            
        for (WeaponAPI w : ship.getAllWeapons()) {
            float reloadRate = w.getAmmoPerSecond();
            if (w.getType() == WeaponType.MISSILE && !w.getSlot().isBuiltIn() && w.usesAmmo() && reloadRate > 0) {
                float ammo = w.getMaxAmmo() + w.getSpec().getAmmoPerSecond() * fabricationTime.get(ship.getHullSize());
                w.getAmmoTracker().setAmmo(Math.round(ammo));
                w.getAmmoTracker().setAmmoPerSecond(0);
            }
        }

        customCombatData.put("MHMods_prefabricator" + id, true);
    }
}
