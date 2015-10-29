package net.omniscimus.fireworks;

import java.util.Random;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

/**
 * Represents the core of a Fireworks show.
 *
 * @author Omniscimus
 */
public class ShowRunnable implements Runnable {

    public ShowRunnable(Location loc) {
	this.location = loc;
    }

    private static final Random random = new Random();
    private static final Type[] types = {
	Type.BALL, Type.BALL_LARGE, Type.BURST, Type.STAR
    };
    private static final Color[] colors = {
	Color.AQUA, Color.BLUE,
	Color.FUCHSIA, Color.GREEN, Color.LIME, Color.MAROON, Color.NAVY,
	Color.OLIVE, Color.ORANGE, Color.PURPLE, Color.RED, Color.SILVER,
	Color.WHITE, Color.TEAL, Color.YELLOW
    };

    // Location of one show instance
    private final Location location;

    /**
     * Spawn one very random piece of fireworks. This will be done in a
     * scheduleSyncRepeatingTask.
     */
    @Override
    public void run() {
	shootFirework(location);
    }

    /**
     * Shoot a piece of random fireworks. This method can also be called without
     * scheduling a task.
     *
     * @param loc where the fireworks should spawn
     */
    public static void shootFirework(Location loc) {
	Firework firework = (Firework) loc.getWorld()
		.spawnEntity(loc, EntityType.FIREWORK);
	FireworkMeta fireworkMeta = firework.getFireworkMeta();

	fireworkMeta.addEffect(
		FireworkEffect.builder()
		.flicker(random.nextBoolean())
		.withColor(colors[random.nextInt(14)])
		.withFade(colors[random.nextInt(14)])
		.with(types[random.nextInt(3)])
		.trail(random.nextBoolean())
		.build()
	);
	fireworkMeta.setPower(random.nextInt(2) + 1);
	firework.setFireworkMeta(fireworkMeta);
    }

}
