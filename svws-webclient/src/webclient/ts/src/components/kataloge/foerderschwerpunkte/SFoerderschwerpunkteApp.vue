<template>
	<div v-if="visible">
		<svws-ui-header>
			<div class="flex items-center">
				<span class="inline-block mr-3">{{ auswahl?.text }}</span>
				<svws-ui-badge type="light" title="ID">
					<i-ri-fingerprint-line />
					{{ auswahl?.id }}
				</svws-ui-badge>
			</div>
			<div>
				<span class="opacity-50">{{ auswahl?.kuerzel }}</span>
			</div>
		</svws-ui-header>
		<svws-ui-router-tab-bar :routes="routeKatalogFoerderschwerpunkte.children_records" :hidden="children_hidden" v-model="selectedRoute">
			<router-view />
		</svws-ui-router-tab-bar>
	</div>
	<div v-else class="app-layout--main--placeholder">
		<i-ri-archive-line />
	</div>
</template>

<script setup lang="ts">

	import { computed, ComputedRef } from "vue";
	import { routeKatalogFoerderschwerpunkte } from "~/router/apps/RouteKatalogFoerderschwerpunkte";
	import { FoerderschwerpunkteAppProps } from "./SFoerderschwerpunkteAppProps";

	const props = defineProps<FoerderschwerpunkteAppProps>();

	const selectedRoute = routeKatalogFoerderschwerpunkte.childRouteSelector;
	const children_hidden = routeKatalogFoerderschwerpunkte.children_hidden();

	const visible: ComputedRef<boolean> = computed(() => props.auswahl !== undefined);

</script>
