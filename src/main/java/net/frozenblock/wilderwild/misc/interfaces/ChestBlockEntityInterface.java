package net.frozenblock.wilderwild.misc.interfaces;

public interface ChestBlockEntityInterface {

	boolean getCanBubble();

	void setCanBubble(boolean b);

	void setBubbleTicks(int i);

	int getBubbleTick();

	void bubble();
}
