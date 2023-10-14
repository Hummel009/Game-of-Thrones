package got.client.sound;

import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import java.util.*;

public class GOTMusicResourceManager implements IResourceManager {
	public Map<ResourceLocation, IResource> resourceMap = new HashMap<>();

	@Override
	public List<IResource> getAllResources(ResourceLocation resource) {
		List<IResource> list = new ArrayList<>();
		list.add(getResource(resource));
		return list;
	}

	@Override
	public IResource getResource(ResourceLocation resource) {
		return resourceMap.get(resource);
	}

	@Override
	public Set<Map.Entry<ResourceLocation, IResource>> getResourceDomains() {
		return resourceMap.entrySet();
	}

}
