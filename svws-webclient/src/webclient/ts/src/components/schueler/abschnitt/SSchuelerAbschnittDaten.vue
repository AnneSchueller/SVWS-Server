<template>
	<div v-if="visible" class="app-container">
		<s-card-schueler-halbjahr :data="data!" @patch="patch" :map-lehrer="mapLehrer" :map-jahrgaenge="mapJahrgaenge" :map-klassen="mapKlassen"
			:schule="schule" :map-foerderschwerpunkte="mapFoerderschwerpunkte" />
		<s-card-schueler-zeugnis-abschluss :data="data!" @patch="patch" @patch-bemerkungen="patchBemerkungen" :map-klassen="mapKlassen" />
		<s-card-schueler-abschluss-berechnung :data="data!" @patch="patch" />
		<s-card-schueler-fehlzeiten :data="data!" @patch="patch" />
	</div>
</template>

<script setup lang="ts">

	import { computed, ComputedRef } from "vue";
	import { SchuelerAbschnittDatenProps } from "./SSchuelerAbschnittDatenProps";

	const props = defineProps<SchuelerAbschnittDatenProps>();

	const visible: ComputedRef<boolean> = computed(() => props.data !== undefined);

</script>
