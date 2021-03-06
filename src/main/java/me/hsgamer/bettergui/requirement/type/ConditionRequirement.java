package me.hsgamer.bettergui.requirement.type;

import me.hsgamer.bettergui.api.requirement.BaseRequirement;
import me.hsgamer.bettergui.config.MessageConfig;
import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import me.hsgamer.hscore.common.CollectionUtils;
import me.hsgamer.hscore.expression.ExpressionUtils;
import me.hsgamer.hscore.variable.VariableManager;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class ConditionRequirement extends BaseRequirement<Boolean> {
  public ConditionRequirement(String name) {
    super(name);
  }

  @Override
  public Boolean getParsedValue(UUID uuid) {
    List<String> list = CollectionUtils.createStringListFromObject(value, true);
    list.replaceAll(s -> VariableManager.setVariables(s, uuid));
    for (String s : list) {
      if (!ExpressionUtils.isBoolean(s)) {
        MessageUtils.sendMessage(uuid, MessageConfig.INVALID_CONDITION.getValue().replace("{input}", s));
        continue;
      }
      if (ExpressionUtils.getResult(s).equals(BigDecimal.ZERO)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean check(UUID uuid) {
    return getParsedValue(uuid);
  }

  @Override
  public void take(UUID uuid) {
    // EMPTY
  }
}
