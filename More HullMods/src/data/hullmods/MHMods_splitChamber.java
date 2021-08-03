package data.hullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.combat.WeaponAPI;
import com.fs.starfarer.api.combat.WeaponAPI.WeaponType;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import org.lwjgl.util.vector.Vector2f;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MHMods_splitChamber extends BaseHullMod {

    final float fireRate = 100f;
    final float damage = 40f;
    final float flux = 50f;

    @Override
    public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
        stats.getEnergyRoFMult().modifyMult(id, 1 + fireRate * 0.01f);
        stats.getEnergyWeaponFluxCostMod().modifyMult(id, 1 - flux * 0.01f);
        stats.getEnergyWeaponDamageMult().modifyMult(id, 1 - damage * 0.01f);
        stats.getEnergyAmmoBonus().modifyMult(id, 1 + fireRate * 0.01f);
        stats.getDynamic().getStat("ENERGY_AMMO_REGEN").modifyMult("MHMods_splitChamber", 1 + fireRate * 0.01f);

        stats.getBallisticRoFMult().modifyMult(id, 1 + fireRate * 0.01f);
        stats.getBallisticWeaponFluxCostMod().modifyMult(id, 1 - flux * 0.01f);
        stats.getBallisticWeaponDamageMult().modifyMult(id, 1 - damage * 0.01f);
        stats.getBallisticAmmoBonus().modifyMult(id, 1 + fireRate * 0.01f);
        stats.getDynamic().getStat("BALLISTIC_AMMO_REGEN").modifyMult("MHMods_splitChamber", 1 + fireRate * 0.01f);

        /*
        stats.getBeamWeaponFluxCostMult().modifyMult(id, 1 - flux * 0.01f);
        stats.getBeamWeaponDamageMult().modifyMult(id, 1 - damage * 0.01f);
        stats.getDynamic().getStat("BALLISTIC_AMMO_REGEN").modifyMult("MHMods_splitChamber", 1 / (1 + fireRate * 0.01f));
         */
    }

    @Override
    public String getDescriptionParam(int index, HullSize hullSize) {
        if (index == 0) return Math.round(fireRate) + "%";
        if (index == 1) return Math.round(flux) + "%";
        if (index == 2) return Math.round(damage) + "%";
        return null;
    }

    @Override
    public void advanceInCombat(ShipAPI ship, float amount) {
        if (!ship.isAlive()) return;
        if (ship.getFullTimeDeployed() >= 0.5f) return;

        Map<String, Object> customCombatData = Global.getCombatEngine().getCustomData();
        String id = ship.getId();

        if (customCombatData.get("MHMods_splitChamber" + id) instanceof Boolean) return;

        MutableShipStatsAPI stats = ship.getMutableStats();

        for (WeaponAPI w : ship.getAllWeapons()) {
            float reloadRate = w.getAmmoPerSecond();
            float ammoRegen = 1;
            if (w.isBeam()) ammoRegen *= stats.getDynamic().getStat("BALLISTIC_AMMO_REGEN").getModifiedValue();
            if (w.getType() == WeaponType.ENERGY && w.usesAmmo() && reloadRate > 0) {
                ammoRegen *= stats.getDynamic().getStat("ENERGY_AMMO_REGEN").getModifiedValue();
                w.getAmmoTracker().setAmmoPerSecond(w.getAmmoTracker().getAmmoPerSecond() * ammoRegen);
            } else if (w.getType() == WeaponType.BALLISTIC && w.usesAmmo() && reloadRate > 0){
                ammoRegen *= stats.getDynamic().getStat("BALLISTIC_AMMO_REGEN").getModifiedValue();
                w.getAmmoTracker().setAmmoPerSecond(w.getAmmoTracker().getAmmoPerSecond() * ammoRegen);
            }
        }

        customCombatData.put("MHMods_splitChamber" + id, true);
    }
}
