package com.shinoow.abyssalcraft.common.entity.ai;

import java.util.Random;

import com.shinoow.abyssalcraft.common.entity.EntityDreadguard;

import net.minecraft.block.Block;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class EntityAIDreadguardAttack extends EntityAIAttackOnCollide {
	private EntityDreadguard dreadguard;
	private int ticks;
   
    public EntityAIDreadguardAttack(EntityDreadguard dreadguard, double speedTowardsTarget, boolean longMemory) {
        super(dreadguard, speedTowardsTarget, longMemory);
        this.dreadguard = dreadguard;
        this.setMutexBits(7);
    }

    @Override
    public void updateTask() {
        super.updateTask();
        ticks++;
        if (ticks == 20 && !this.dreadguard.isInWater()) {
        	ticks = 0;
        	DestroyAroundMe(0, 0.25F);
        	DestroyAroundMe(1, 0.5F);
        	DestroyAroundMe(2, 0.75F);
        }
    }
    
    private void DestroyAroundMe(int yOffset, float destroyChance) {
    	final World world = this.dreadguard.worldObj;
    	final float hardnessTreshold = 5;
    	final Random random = new Random();
    	
    	for (int dx = -1; dx <= 1; ++dx) {
    		for (int dz = -1; dz <= 1; ++dz) {
    			if (dx == 0 && dz == 0) {
    				continue;
    			}

    			final int x = (int)(this.dreadguard.posX + dx);
    			final int y = (int)(this.dreadguard.posY + yOffset);
    			final int z = (int)(this.dreadguard.posZ + dz);
    			final Block block = world.getBlock(x, y, z);
    	    	if (block != Blocks.air) {
    	        	final float hardness = block.getBlockHardness(world, x, y, z);
					if (hardness < hardnessTreshold && hardness >= 0) {
    	            	if (random.nextFloat() < destroyChance) {
    	            		block.dropBlockAsItemWithChance(world, x, y, z, world.getBlockMetadata(x, y, z), 1.0F, 0);
    	            	}
					}
    	    	}
    		} 
    	}
    }
}