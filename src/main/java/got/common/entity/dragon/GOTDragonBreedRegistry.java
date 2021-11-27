package got.common.entity.dragon;

import java.util.*;

public class GOTDragonBreedRegistry {

	public static GOTDragonBreedRegistry instance;

	public Map<String, GOTDragonBreed> breeds = new HashMap<>();

	public GOTDragonBreedRegistry() {
		add(new GOTDragonBreed("body", "body", 0x2d6e00));
	}

	public void add(GOTDragonBreed breed) {
		breeds.put(breed.getName(), breed);
	}

	public GOTDragonBreed getBreedByName(String name) {
		return breeds.get(name);
	}

	public List<GOTDragonBreed> getBreeds() {
		return new ArrayList<>(breeds.values());
	}

	public static GOTDragonBreedRegistry getInstance() {
		if (instance == null) {
			instance = new GOTDragonBreedRegistry();
		}

		return instance;
	}
}
