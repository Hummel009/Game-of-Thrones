package got.common.entity.other;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAITasks;

public class GOTEntityUtils {
	public static EntityAITasks.EntityAITaskEntry removeAITask(EntityCreature entity, Class taskClass) {
		int i;
		EntityAITasks.EntityAITaskEntry taskEntry;
		for (i = 0; i < entity.tasks.taskEntries.size(); ++i) {
			taskEntry = (EntityAITasks.EntityAITaskEntry) entity.tasks.taskEntries.get(i);
			if (!taskClass.isAssignableFrom(taskEntry.action.getClass())) {
				continue;
			}
			entity.tasks.removeTask(taskEntry.action);
			return taskEntry;
		}
		for (i = 0; i < entity.targetTasks.taskEntries.size(); ++i) {
			taskEntry = (EntityAITasks.EntityAITaskEntry) entity.targetTasks.taskEntries.get(i);
			if (!taskClass.isAssignableFrom(taskEntry.action.getClass())) {
				continue;
			}
			entity.targetTasks.removeTask(taskEntry.action);
			return taskEntry;
		}
		return null;
	}
}
