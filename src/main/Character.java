package main;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/*
 * 
 * */
public class Character {
	private String name;
	private int level;
	private int maxHP;
	private int HP;
	protected JLabel frontImage;
	protected JLabel backImage;

	/* コンストラクタ */
	public Character(String name, int level, int baseStatsHP) {
		this.name = name;
		this.level = level;
		this.maxHP = (baseStatsHP * 2) * level / 100 + (10 + level);
		this.HP = maxHP;
		this.frontImage = new JLabel(new ImageIcon(new ImageIcon("src/" + name + "/front_default.png").getImage().getScaledInstance(250, 250, java.awt.Image.SCALE_SMOOTH)));
		this.frontImage.setBounds(250, 0, 250, 250);
		this.backImage = new JLabel(new ImageIcon(new ImageIcon("src/" + name + "/back_default.png").getImage().getScaledInstance(250, 250, java.awt.Image.SCALE_SMOOTH)));
		this.backImage.setBounds(0, 200, 250, 250);
	}

	public void damage(int num) {
		this.HP = Math.max(0, this.HP - num);
	}

	public void recovery(int num) {
		this.HP = Math.min(this.HP + num, this.maxHP);
	}

	/*
	 * ゲッター
	 * */
	public String getName() {
		return name;
	}

	public int getLevel() {
		return level;
	}

	public int getMaxHP() {
		return maxHP;
	}

	public int getHP() {
		return HP;
	}
}