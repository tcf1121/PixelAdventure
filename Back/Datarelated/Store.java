package Back.Datarelated;

import java.io.Serializable;

public class Store  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int potion;
	private int skill_card;
	private int manaskill;
	private int equipment1;
	private int equipment2;
	private int equipment3;
	private int skill1;
	private int skill2;
	private int skill3;
	
	public Store(int potion, int skill_card, int manaskill, int equipment1, int equipment2, int equipment3, int skill1,
			int skill2, int skill3) {
		setPotion(potion);
		setSkill_card(skill_card);
		setManaskill(manaskill);
		setEquipment1(equipment1);
		setEquipment2(equipment2);
		setEquipment3(equipment3);
		setSkill1(skill1);
		setSkill2(skill2);
		setSkill3(skill3);
	}
	
	public int getPotion() {	return potion;}
	public void setPotion(int potion) {	this.potion = potion;}
	
	public int getSkill_card() {	return skill_card;}
	public void setSkill_card(int skill_card) {	this.skill_card = skill_card;}
	
	public int getManaskill() {	return manaskill;}
	public void setManaskill(int manaskill) {	this.manaskill = manaskill;}
	
	public int getEquipment1() {	return equipment1;}
	public void setEquipment1(int equipment1) {	this.equipment1 = equipment1;}
	
	public int getEquipment2() {	return equipment2;}
	public void setEquipment2(int equipment2) {	this.equipment2 = equipment2;}
	
	public int getEquipment3() {	return equipment3;}
	public void setEquipment3(int equipment3) {	this.equipment3 = equipment3;}
	
	public int getSkill1() {	return skill1;}
	public void setSkill1(int skill1) {	this.skill1 = skill1;}
	
	public int getSkill2() {	return skill2;}
	public void setSkill2(int skill2) {	this.skill2 = skill2;}
	
	public int getSkill3() {	return skill3;}
	public void setSkill3(int skill3) {	this.skill3 = skill3;}
	
}
