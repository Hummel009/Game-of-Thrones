package got.common.entity.dragon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GOTDragonBreedRegistry {

	private static GOTDragonBreedRegistry instance;

	private final Map<String, GOTDragonBreed> breeds = new HashMap<>();

	private GOTDragonBreedRegistry() {
		add(new GOTDragonBreed("body", "body", 0x2d6e00));
	}

	public static GOTDragonBreedRegistry getInstance() {
		if (instance == null) {
			instance = new GOTDragonBreedRegistry();
		}

		return instance;
	}

	private void add(GOTDragonBreed breed) {
		breeds.put(breed.getName(), breed);
	}

	public GOTDragonBreed getBreedByName(String name) {
		return breeds.get(name);
	}

	public List<GOTDragonBreed> getBreeds() {
		return new ArrayList<>(breeds.values());
	}
}
