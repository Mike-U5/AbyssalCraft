/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2019 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.api.necronomicon.condition.caps;

import java.util.ArrayList;
import java.util.List;

import com.shinoow.abyssalcraft.api.necronomicon.condition.ConditionProcessorRegistry;
import com.shinoow.abyssalcraft.api.necronomicon.condition.IUnlockCondition;

import net.minecraft.entity.player.EntityPlayer;

public class NecroDataCapability implements INecroDataCapability {

	List<String> biome_triggers = new ArrayList<>();
	List<String> entity_triggers = new ArrayList<>();
	List<Integer> dimension_triggers = new ArrayList<>();
	List<String> artifact_triggers = new ArrayList<>();
	List<String> page_triggers = new ArrayList<>();
	List<String> whisper_triggers = new ArrayList<>();
	List<String> misc_triggers = new ArrayList<>();

	boolean hasAllKnowledge;

	long lastSyncTime = 0;
	int syncTimer = 0;

	public static INecroDataCapability getCap(EntityPlayer player){
		return player.getCapability(NecroDataCapabilityProvider.NECRO_DATA_CAP, null);
	}

	@Override
	public boolean isUnlocked(IUnlockCondition cond, EntityPlayer player) {

		if(cond.getType() == -1 || hasAllKnowledge && cond.getType() != 11) return true;
		else return ConditionProcessorRegistry.instance().getProcessor(cond.getType()).processUnlock(cond, this, player);
	}

	@Override
	public void triggerEntityUnlock(String name) {
		if(name != null && !entity_triggers.contains(name) && name.contains(":"))
			entity_triggers.add(name);
	}

	@Override
	public void triggerBiomeUnlock(String name) {
		if(name != null && !biome_triggers.contains(name))
			biome_triggers.add(name);
	}

	@Override
	public void triggerDimensionUnlock(int id) {
		if(!dimension_triggers.contains(id))
			dimension_triggers.add(id);
	}

	@Override
	public void triggerArtifactUnlock(String name) {
		if(name != null && !artifact_triggers.contains(name))
			artifact_triggers.add(name);
	}

	@Override
	public void triggerPageUnlock(String name) {
		if(name != null && !page_triggers.contains(name))
			page_triggers.add(name);
	}

	@Override
	public void triggerWhisperUnlock(String name) {
		if(name != null && !page_triggers.contains(name))
			page_triggers.add(name);
	}

	@Override
	public void triggerMiscUnlock(String name) {
		if(name != null && !misc_triggers.contains(name))
			misc_triggers.add(name);
	}

	@Override
	public void unlockAllKnowledge(boolean unlock) {
		hasAllKnowledge = unlock;
	}

	@Override
	public void setLastSyncTime(long time) {
		lastSyncTime = time;
	}

	@Override
	public List<String> getBiomeTriggers() {

		return biome_triggers;
	}

	@Override
	public List<String> getEntityTriggers() {

		return entity_triggers;
	}

	@Override
	public List<Integer> getDimensionTriggers() {

		return dimension_triggers;
	}

	@Override
	public List<String> getArtifactTriggers() {

		return artifact_triggers;
	}

	@Override
	public List<String> getPageTriggers() {

		return page_triggers;
	}

	@Override
	public List<String> getWhisperTriggers() {

		return whisper_triggers;
	}

	@Override
	public List<String> getMiscTriggers(){

		return misc_triggers;
	}

	@Override
	public boolean hasUnlockedAllKnowledge(){
		return hasAllKnowledge;
	}

	@Override
	public long getLastSyncTime() {

		return lastSyncTime;
	}

	@Override
	public void incrementSyncTimer() {
		syncTimer++;
	}

	@Override
	public void resetSyncTimer() {
		syncTimer = 0;
	}

	@Override
	public int getSyncTimer() {

		return syncTimer;
	}

	@Override
	public void copy(INecroDataCapability cap) {

		biome_triggers = cap.getBiomeTriggers();
		entity_triggers = cap.getEntityTriggers();
		dimension_triggers = cap.getDimensionTriggers();
		artifact_triggers = cap.getArtifactTriggers();
		page_triggers = cap.getPageTriggers();
		whisper_triggers = cap.getWhisperTriggers();
		misc_triggers = cap.getMiscTriggers();
		hasAllKnowledge = cap.hasUnlockedAllKnowledge();
		lastSyncTime = cap.getLastSyncTime();
	}
}
