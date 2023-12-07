package Back.Datarelated;

import java.io.Serializable;

public class Equip  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String explan;
	private String ef_ex;
	private int effect;
	private String rarity;
	
	public Equip(String id, String name, String explan,
			String ef_ex, int effect, String rarity) {
		setId(id);
		setName(name);
		setExplan(explan);
		setEf_ex(ef_ex);
		setEffect(effect);
		setRarity(rarity);
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
	public void setExplan(String explan) {
		this.explan = explan;
	}
	public String getExplan() {
		return this.explan;
	}
	public void setEf_ex(String ef_ex) {
		this.ef_ex = ef_ex;
	}
	public String getEf_ex() {
		return this.ef_ex;
	}
	public void setEffect(int effect) {
		this.effect = effect;
	}
	public int getEffect() {
		return this.effect;
	}
	public void setRarity(String rarity) {
		this.rarity = rarity;
	}
	public String getRarity() {
		return this.rarity;
	}
}

