<template>
	<svws-ui-content-card class="lg:col-span-2 4xl:col-span-3 self-end">
		<svws-ui-textarea-input placeholder="Bemerkungen" :model-value="data.bemerkungen"
			@update:model-value="doPatch({ bemerkungen: String($event) })" resizeable="vertical" />
	</svws-ui-content-card>
</template>

<script setup lang="ts">

	import { SchuelerStammdaten } from "@svws-nrw/svws-core-ts";

	const props = defineProps<{
		data: SchuelerStammdaten;
	}>();

	const emit = defineEmits<{
		(e: 'patch', data: Partial<SchuelerStammdaten>): void;
	}>()

	function doPatch(data: Partial<SchuelerStammdaten>) {
		emit('patch', data);
	}

</script>
