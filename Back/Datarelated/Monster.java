package Back.Datarelated;

import java.io.Serializable;

public class Monster  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int Strength;
	private int Hp;
	private int Exp;
	public Monster(int Str, int Hp, int Exp){
		setStr(Str);
		setHp(Hp);
		setExp(Exp);
	}
	
	public void setStr(int Strength) {
		this.Strength = Strength;
	}
	public int getStr() {
		return this.Strength;
	}
	public void setHp(int Hp) {
		this.Hp = Hp;
	}
	public int getHp() {
		return this.Hp;
	}
	public void setExp(int Exp) {
		this.Exp = Exp;
	}
	public int getExp() {
		return this.Exp;
	}
	public void damage(int Str) {
		this.Hp -= Str;
		if(this.Hp <0) {
			this.Hp =0;
		}
	}
	
    public void addMon(int difficulty) {
    	switch (difficulty) {
		case 0:
			this.setStr(1);
			this.setHp(1);
			this.setExp(1);
			break;
		case 1:
			this.setStr(5);
			this.setHp(10);
			this.setExp(3);
			break;
		case 2:
			this.setStr(7);
			this.setHp(15);
			this.setExp(7);
			break;
		case 3:
			this.setStr(9);
			this.setHp(20);
			this.setExp(11);
			break;
		case 4:
			this.setStr(12);
			this.setHp(30);
			this.setExp(15);
			break;
		case 5:
			this.setStr(15);
			this.setHp(35);
			this.setExp(17);
			break;
		case 6:
			this.setStr(18);
			this.setHp(45);
			this.setExp(25);
			break;
		case 7:
			this.setStr(23);
			this.setHp(50);
			this.setExp(30);
			break;
		case 8:
			this.setStr(25);
			this.setHp(60);
			this.setExp(33);
			break;
		case 9:
			this.setStr(30);
			this.setHp(80);
			this.setExp(40);
			break;
		case 10:
			this.setStr(35);
			this.setHp(500);
			this.setExp(100);
			break;
		default:
			break;
		}
    }
}
