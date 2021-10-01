package data.hullmods;

import Utilities.MHMods_utilities;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.combat.ShipVariantAPI;
import com.fs.starfarer.api.impl.hullmods.BaseLogisticsHullMod;

public class MHMods_ExplorationRefit extends BaseLogisticsHullMod {

    public float
            FuelUse = 0.2f,
            SupUse = 0.2f,
            SensorStrength = 50f,
            CR = 30f,
            CRRecovery = 0.25f,
            EOMult = 0.8f,
            MaxCargoFuelMulti = 0.85f;


    public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
        ShipVariantAPI variant = stats.getVariant();
        float mult = 1f;
        if (variant != null && variant.hasHullMod("efficiency_overhaul")){
            mult = EOMult;
        }
        stats.getFuelUseMod().modifyMult(id, 1 - (FuelUse * mult));
        stats.getSuppliesPerMonth().modifyMult(id,  1 - (SupUse * mult));
        stats.getSensorStrength().modifyPercent(id, SensorStrength * mult);
        stats.getMaxCombatReadiness().modifyFlat(id, (-CR * mult) / 100);
        stats.getBaseCRRecoveryRatePercentPerDay().modifyMult(id, 1 - (CRRecovery * mult));
        stats.getCargoMod().modifyMult(id, MaxCargoFuelMulti);
        stats.getFuelMod().modifyMult(id, MaxCargoFuelMulti);
    }

    public String getDescriptionParam(int index, HullSize hullSize, ShipAPI ship) {
        if (index == 0) return MHMods_utilities.floatToString(FuelUse * 100) + "%";
        if (index == 1) return MHMods_utilities.floatToString(SupUse * 100) + "%";
        if (index == 2) return MHMods_utilities.floatToString(SensorStrength) + "%";
        if (index == 3) return MHMods_utilities.floatToString(CR) + "%";
        if (index == 4) return MHMods_utilities.floatToString(CRRecovery * 100) + "%";
        if (index == 5) return MHMods_utilities.floatToString(100 - EOMult * 100) + "%";
        if (index == 6) return MHMods_utilities.floatToString(100 - MaxCargoFuelMulti * 100) + "%";
        return null;
    }
}