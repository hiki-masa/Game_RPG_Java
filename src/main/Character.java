package main;

/*
 * 
 * */
public class Character {
	private String name;
	private int maxHP;
	private int HP;

	public Character(String name, int maxHP) {
		this.name = name;
		this.maxHP = maxHP;
		this.HP = maxHP;
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

	public int getMaxHP() {
		return maxHP;
	}

	public int getHP() {
		return HP;
	}
}