package com.shinoow.abyssalcraft.common.entity.anti;

import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityAbomination extends EntitySlime {

	public EntityAbomination(World world) {
		super(world);
		this.setSlimeSize(2);
	}

	@Override
	protected void updateEntityActionState() {
		this.despawnEntity();
	}

	@Override
	public boolean attackEntityFrom(DamageSource src, float amount) {
		return super.attackEntityFrom(src, 0);
	}

	@Override
	public void setDead() {
		this.isDead = true;
	}

	@Override
	public void setHealth(float p_70606_1_) {
		this.dataWatcher.updateObject(6, Float.valueOf(MathHelper.clamp_float(p_70606_1_, 0.0F, this.getMaxHealth())));
	}

	@Override
	protected String getSlimeParticle() {
		return "depthsuspend";
	}

	@Override
	protected String getHurtSound() {
		return "abyssalcraft:abdomination.hurt";
	}

	@Override
	protected boolean canDamagePlayer() {
		return false;
	}

	@Override
	protected int getAttackStrength() {
		return 0;
	}

	@Override
	protected boolean canDespawn() {
		return false;
	}
}
