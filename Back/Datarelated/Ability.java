package Back.Datarelated;

import java.io.Serializable;

public class Ability  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String class_no;
	private int strenght;
	private int defense	;
	private int health;
	private int mana;	

	
    public Ability(String class_no, int strenght, int defense, int health,
    		int mana) {
    	setcl(class_no);
        setStr(strenght);
        setDef(defense);
        setHp(health);
        setMp(mana);
    }
    public String getcl() {
        return class_no;
    }
    public void setcl(String class_no) {
        this.class_no = class_no;
    }
    public int getStr() {
        return strenght;
    }
    public void setStr(int str) {
        this.strenght = str;
    }
    public void upStr(int str) {
        this.strenght += str;
    }
    public int getDef() {
        return defense;
    }
    public void setDef(int def) {
        this.defense = def;
    }
    public void upDef(int def) {
        this.defense += def;
    }
    public int getHp() {
        return health;
    }
    public void setHp(int hp) {
        this.health = hp;
    }
    public void upHp(int hp) {
        this.health += hp;
    }
    public int getMp() {
        return mana;
    }
    public void setMp(int mp) {
        this.mana = mp;
    }
    public void upMp(int mp) {
        this.mana += mp;
    }
}
