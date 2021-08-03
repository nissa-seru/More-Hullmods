package data.hullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipCommand;

public class MHMods_EmergencyVentingSystemV2 extends BaseHullMod {

    private final float maxOverload = 2f;

    public String getDescriptionParam(int index, ShipAPI.HullSize hullSize) {
        if (index == 0 || index == 1) return Math.round(maxOverload) + "";
        return null;
    }

    @Override
    public void advanceInCombat(ShipAPI ship, float amount) {

        if (!ship.isAlive()) return;

        boolean ShouldVent = false;

        if (Global.getCombatEngine().getCustomData().get("MHM_EVS_ShouldVent" + ship.getId()) instanceof Boolean)
            ShouldVent = (boolean) Global.getCombatEngine().getCustomData().get("MHM_EVS_ShouldVent" + ship.getId());

        if (ship.getFluxTracker().getOverloadTimeRemaining() >= maxOverload && ship.getMutableStats().getVentRateMult().getModifiedValue() > 0 && !ShouldVent) {
            ship.getFluxTracker().stopOverload();
            ship.getFluxTracker().beginOverloadWithTotalBaseDuration(maxOverload);
            ship.getEngineController().forceFlameout();
            ShouldVent = true;
        }
        if (ShouldVent && !ship.getFluxTracker().isOverloadedOrVenting()) {
            ship.giveCommand(ShipCommand.VENT_FLUX, true, 1);
            ShouldVent = false;
        }

        Global.getCombatEngine().getCustomData().put("MHM_EVS_ShouldVent" + ship.getId(), ShouldVent);
    }
}
