<template>
	<svws-ui-content-card title="Grundschulbesuch">
		<div class="input-wrapper">
			<svws-ui-text-input placeholder="Einschulung" :model-value="data.grundschuleEinschulungsjahr"
				@update:model-value="doPatch({ grundschuleEinschulungsjahr: Number($event) })" type="text" />
			<svws-ui-text-input placeholder="Einschulungsart" :model-value="data.grundschuleEinschulungsartID"
				@update:model-value="doPatch({ grundschuleEinschulungsartID: Number($event) })" />
			<svws-ui-text-input placeholder="EP-Jahre" :model-value="data.grundschuleJahreEingangsphase"
				@update:model-value="doPatch({ grundschuleJahreEingangsphase: Number($event) })" />
			<svws-ui-text-input placeholder="Übergangsempfehlung Jg. 5" :model-value="data.grundschuleUebergangsempfehlungID"
				@update:model-value="doPatch({ grundschuleUebergangsempfehlungID: Number($event) })" type="text" />
		</div>
	</svws-ui-content-card>
</template>

<script setup lang="ts">

	import { SchuelerSchulbesuchsdaten } from "@svws-nrw/svws-core-ts";

	defineProps<{
		data: SchuelerSchulbesuchsdaten;
	}>();

	const emit = defineEmits<{
		(e: 'patch', data: Partial<SchuelerSchulbesuchsdaten>): void;
	}>()

	function doPatch(data: Partial<SchuelerSchulbesuchsdaten>) {
		emit('patch', data);
	}

</script>
