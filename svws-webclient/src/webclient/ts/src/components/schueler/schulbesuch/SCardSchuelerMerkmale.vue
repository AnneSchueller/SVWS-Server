<template>
	<svws-ui-content-card title="Besondere Merkmale für die Statistik">
		<div>
			Hier wird demnächst die Unterstützung für Merkmale implementiert
			<div v-for="merkmal in data.merkmale" :key="merkmal.id">
				<!-- TODO <svws-zu-table> für die Merkmale -->
				<p> {{ merkmal.id + " (" + merkmal.datumVon + "-" + merkmal.datumBis + ")" }} </p>
			</div>
		</div>
	</svws-ui-content-card>
</template>

<script setup lang="ts">

	import { SchuelerSchulbesuchsdaten } from "@svws-nrw/svws-core-ts";

	defineProps<{
		data: SchuelerSchulbesuchsdaten;
	}>();

</script>
