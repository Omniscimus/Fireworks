package net.omniscimus.fireworks;

import java.util.Random;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class Show implements Runnable {
	
	public Show(Location loc) {
		this.location = loc;
	}
	
	private static Random random = new Random();
	private static Type[] types = {Type.BALL, Type.BALL_LARGE, Type.BURST, Type.STAR};
	private static Color[] colors = {Color.AQUA, Color.BLUE, Color.FUCHSIA, Color.GREEN, Color.LIME, Color.MAROON, Color.NAVY, Color.OLIVE, Color.ORANGE, Color.PURPLE, Color.RED, Color.SILVER, Color.WHITE, Color.TEAL, Color.YELLOW};
	
	// Location of one show instance
	private Location location;
	
	// Spawn one very random piece of fireworks. This'll be done in a scheduleSyncRepeatingTask.
	@Override
	public void run() {

		Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
		FireworkMeta fireworkMeta = firework.getFireworkMeta();
		
		fireworkMeta.addEffect(FireworkEffect.builder().flicker(random.nextBoolean()).withColor(colors[random.nextInt(14)]).withFade(colors[random.nextInt(14)]).with(types[random.nextInt(3)]).trail(random.nextBoolean()).build());
		fireworkMeta.setPower(random.nextInt(2) + 1);
		firework.setFireworkMeta(fireworkMeta);

	}
	
	/*
	final Player p = (Player) sender;

	if(commandLabel.equalsIgnoreCase("stopfireworks")) {
		Bukkit.getScheduler().cancelTask(task);
		p.sendMessage("Runnable returned to initial state.");
	}

	if (commandLabel.equalsIgnoreCase("startfireworks")) {
		
		locs[0] = new Location(p.getWorld(), -454, 77, 107);
		locs[1] = new Location(p.getWorld(), -454, 77, 103);
		locs[2] = new Location(p.getWorld(), -482, 77, 135);
		locs[3] = new Location(p.getWorld(), -486, 77, 135);
		locs[4] = new Location(p.getWorld(), -482, 77, 075);
		locs[5] = new Location(p.getWorld(), -486, 77, 075);
		locs[6] = new Location(p.getWorld(), -510, 77, 103);
		locs[7] = new Location(p.getWorld(), -510, 77, 107);

		locationToSpawn = 0;
		
		sender.sendMessage(ChatColor.GOLD + "Locations initialized, ready for takeoff... Igniting chemicals.");
		
		task = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
				
				@Override
				public void run() {

					fw = (Firework) p.getWorld().spawnEntity(locs[locationToSpawn], EntityType.FIREWORK);
					fwm = fw.getFireworkMeta();
					type = soorten[random.nextInt(3)];
					color = kleuren[random.nextInt(14)];
					fade = kleuren[random.nextInt(14)];
					effect = FireworkEffect.builder().flicker(random.nextBoolean()).withColor(color).withFade(fade).with(type).trail(random.nextBoolean()).build();
					rp = random.nextInt(2) + 1;
					
					fwm.addEffect(effect);
					fwm.setPower(rp);
					fw.setFireworkMeta(fwm);

					if(locationToSpawn < 7) locationToSpawn++;
					else if(locationToSpawn >= 7) locationToSpawn = 0;
					
				}
		
		}, 0, 10);
	}
	return true;
	*/
	/*
	private int locationToSpawn = 0;
	private Random random = new Random();

	private Location[] locs = new Location[8];
	

	private static Type[] soorten = {Type.BALL, Type.BALL_LARGE, Type.BURST, Type.STAR};
	private static Color[] kleuren = {Color.AQUA, Color.BLUE, Color.FUCHSIA, Color.GREEN, Color.LIME, Color.MAROON, Color.NAVY, Color.OLIVE, Color.ORANGE, Color.PURPLE, Color.RED, Color.SILVER, Color.WHITE, Color.TEAL, Color.YELLOW};
	
	private Firework fw;
	private FireworkMeta fwm;
	private Type type;
	private Color color;
	private Color fade;
	private FireworkEffect effect;
	private int rp;
	*/
	
}
