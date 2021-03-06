package com.timvisee.safecreeper.block.state;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CommandBlock;
import org.bukkit.configuration.ConfigurationSection;

import com.timvisee.safecreeper.block.SCBlockLocation;

public class SCCommandBlockState extends SCBlockState {

	private String blockName;
	private String cmd;
	
	/**
	 * Constructor
	 * @param cb Command block
	 */
	public SCCommandBlockState(CommandBlock cb) {
		// Construct the parent class
		super(cb.getBlock());
		
		// Store the state of the command block
		this.blockName = cb.getName();
		this.cmd = cb.getCommand();
	}
	
	/**
	 * Constructor
	 * @param b Command block
	 */
	public SCCommandBlockState(Block b) {
		this((CommandBlock) b.getState());
	}
	
	/**
	 * Constructor
	 * @param loc Block location
	 * @param blockName Block name
	 * @param cmd Command
	 */
	public SCCommandBlockState(SCBlockLocation loc, String blockName, String cmd) {
		// Construct the parent class
		super(loc, Material.COMMAND.getId(), (byte) 0);
		
		// Store the block name and the command
		this.blockName = blockName;
		this.cmd = cmd;
	}
	
	/**
	 * Get the command block
	 * @return Command block
	 */
	public CommandBlock getCommandBlock() {
		return (CommandBlock) getBlock().getState();
	}
	
	/**
	 * Get the command block name
	 * @return Block namee
	 */
	public String getBlockName() {
		return this.blockName;
	}
	
	/**
	 * Set the command block name
	 * @param blockName Block name
	 */
	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}
	
	/**
	 * Get the command block command
	 * @return
	 */
	public String getCommand() {
		return this.cmd;
	}
	
	/**
	 * Set the command block command
	 * @param cmd Command
	 */
	public void setCommand(String cmd) {
		this.cmd = cmd;
	}
	
	/**
	 * Get the block state type
	 */
	public SCBlockStateType getStateType() {
		return SCBlockStateType.COMMAND_BLOCK;
	}
	
	/**
	 * Apply the block state to the block
	 * @return True if succeed
	 */
	public boolean apply() {
		if(!super.apply())
			return false;
		
		// Get the command block
		CommandBlock cb = getCommandBlock();
		
		// Make sure the command block is not null
		if(cb == null)
			return false;
		
		// Set the name and the command of the command block
		cb.setName(this.blockName);
		cb.setCommand(this.cmd);
		
		// Update the command block
		cb.update();
		
		// Return true
		return true;
	}
	
	/**
	 * Save the data in a configuration section
	 * @param configSection Configuration section to store the data in
	 */
	public void save(ConfigurationSection configSection) {
		// Make sure the param is not null
		if(configSection == null)
			return;
		
		// Save the main data from the parent class
		super.save(configSection);
		
		// Store the sign lines
		configSection.set("blockName", this.blockName);
		configSection.set("blockCmd", this.cmd);
	}

	/**
	 * Load the data in a configuration section
	 * @param configSection Configuration section to store the data in
	 */
	public static SCCommandBlockState load(ConfigurationSection configSection) {
		// Make sure the param is not null
		if(configSection == null)
			return null;
		
		// Get the block location
		ConfigurationSection locSection = configSection.getConfigurationSection("loc");
		SCBlockLocation loc = SCBlockLocation.load(locSection);
		
		// Get the block name and the selected command
		String blockName = configSection.getString("blockName", "Command Block");
		String cmd = configSection.getString("blockCmd", "");
		
		// Construct the command block and return it
		return new SCCommandBlockState(loc, blockName, cmd);
	}
}
