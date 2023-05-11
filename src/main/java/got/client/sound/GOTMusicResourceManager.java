package got.client.sound;

import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.SimpleResource;
import net.minecraft.util.ResourceLocation;

import java.io.InputStream;
import java.util.*;

public class GOTMusicResourceManager implements IResourceManager {
	public Map<ResourceLocation, IResource> resourceMap = new HashMap<>();

	@Override
	public List getAllResources(ResourceLocation resource) {
		ArrayList<IResource> list = new ArrayList<>();
		list.add(getResource(resource));
		return list;
	}

	@Override
	public IResource getResource(ResourceLocation resource) {
		return resourceMap.get(resource);
	}

	@Override
	public Set getResourceDomains() {
		return resourceMap.entrySet();
	}

	public void registerMusicResources(ResourceLocation resource, InputStream in) {
		SimpleResource ires = new SimpleResource(resource, in, null, null);
		resourceMap.put(resource, ires);
	}
}
