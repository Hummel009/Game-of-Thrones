package got.client.sound;

import java.io.*;
import java.util.*;

import net.minecraft.client.resources.*;
import net.minecraft.util.ResourceLocation;

public class GOTMusicResourceManager implements IResourceManager {
	public Map<ResourceLocation, IResource> resourceMap = new HashMap<>();

	@Override
	public List getAllResources(ResourceLocation resource) throws IOException {
		ArrayList<IResource> list = new ArrayList<>();
		list.add(getResource(resource));
		return list;
	}

	@Override
	public IResource getResource(ResourceLocation resource) throws IOException {
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
