package Back.Datarelated;

import java.io.Serializable;

public class Skill  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String type;
	private String explan;
	private int effect;
	private int use_mana;
	
	public Skill(String id, String name, String type,
			String explan, int effect, int use_mana){
		setId(id);
		setName(name);
		setType(type);
		setExplan(explan);
		setEffect(effect);
		setUmana(use_mana);
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return this.id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return this.type;
	}
	public void setExplan(String explan) {
		this.explan = explan;
	}
	public String getExplan() {
		return this.explan;
	}
	public void setEffect(int effect) {
		this.effect = effect;
	}
	public int getEffect() {
		return this.effect;
	}
	public void setUmana(int use_mana) {
		this.use_mana = use_mana;
	}
	public int getUmana() {
		return this.use_mana;
	}
}
