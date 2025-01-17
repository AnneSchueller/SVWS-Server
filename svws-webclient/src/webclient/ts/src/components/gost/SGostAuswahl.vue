<template>
	<svws-ui-secondary-menu>
		<template #headline>Abiturjahrgänge</template>
		<template #abschnitt>
			<abschnitt-auswahl :akt-abschnitt="aktAbschnitt" :abschnitte="abschnitte" :set-abschnitt="setAbschnitt" />
		</template>
		<template #header />
		<template #content>
			<div class="container">
				<svws-ui-data-table :clicked="auswahl" clickable @update:clicked="setAbiturjahrgang" :items="rows" :columns="cols" footer>
					<template #cell(abiturjahr)="{ value }">
						{{ value.abiturjahr === -1 ? '' : value.abiturjahr }}
						<span v-if="(pending && value.abiturjahr === auswahl?.abiturjahr)" class="loading-spinner-dimensions">
							<img src="/loading_spinner.svg" alt="Ladeanzeige" class="loading-spinner-dimensions loading-rotation"></span>
					</template>
					<template #footer>
						<svws-ui-dropdown type="icon" class="">
							<template #dropdownButton>Abiturjahr hinzufügen</template>
							<template #dropdownItems>
								<svws-ui-dropdown-item v-for="jahrgang in mapJahrgaengeOhneAbiJahrgang.values()" :key="jahrgang.id" class="px-2" @click="abiturjahr_hinzufuegen(jahrgang)">
									{{ jahrgang.kuerzel }}
								</svws-ui-dropdown-item>
							</template>
						</svws-ui-dropdown>
					</template>
				</svws-ui-data-table>
				<router-view name="gost_child_auswahl" />
			</div>
		</template>
	</svws-ui-secondary-menu>
</template>

<script setup lang="ts">

	import { GostJahrgang, JahrgangsListeEintrag } from "@svws-nrw/svws-core-ts";
	import { DataTableColumn } from "@svws-nrw/svws-ui";
	import { computed, ComputedRef } from "vue";
	import { GostAuswahlProps } from "./SGostAuswahlProps";

	const props = defineProps<GostAuswahlProps>();

	const cols: DataTableColumn[] = [
		{ key: "bezeichnung", label: "Bezeichnung", sortable: true, span: 2 },
		{ key: "abiturjahr", label: "Abiturjahr", sortable: true },
		{ key: "jahrgang", label: "Stufe", sortable: true }];

	const rows: ComputedRef<GostJahrgang[]> = computed(() => {
		const list = [...props.mapAbiturjahrgaenge.values()];
		return list.sort((a, b) => (a?.bezeichnung || "") < (b?.bezeichnung || "") ? 1 : -1)
	});

	const pending: ComputedRef<boolean> = computed(() => props.apiStatus.pending);

	async function abiturjahr_hinzufuegen(jahrgang: JahrgangsListeEintrag) {
		await props.addAbiturjahrgang(jahrgang.id);
	}
</script>

<style lang="postcss">

	.loading-disclaimer {
		background-color: rgba(var(--svws-ui-color-dark-20), var(--tw-border-opacity));
		--tw-bg-opacity: 1;
		--tw-border-opacity: 1;
		border-width: 1px;
		padding: .25rem .75rem;
		line-height: 1.125;
	}

	.loading-spinner-dimensions {
		height: 1rem;
		width: 1rem;
	}

	.loading-display {
		display: flex;
		flex-direction: row;
		justify-content: space-between;
	}

	.loading-rotation {
		display: block;
		position: relative;
		-webkit-animation: spin 6s steps(11, end) infinite;
		-moz-animation: spin 6s steps(11, end) infinite;
		animation: spin 6s steps(11, end) infinite;
	}

	.api-error-text {
		color: rgb(var(--svws-ui-color-error));
		font-weight: 700;
	}

	@-webkit-keyframes spin {
		0% {
			-webkit-transform: rotate(0deg);
		}

		100% {
			-webkit-transform: rotate(360deg);
		}
	}

	@-moz-keyframes spin {
		0% {
			-webkit-transform: rotate(0deg);
		}

		100% {
			-webkit-transform: rotate(360deg);
		}
	}

	@keyframes spin {
		0% {
			-webkit-transform: rotate(0deg);
		}

		100% {
			-webkit-transform: rotate(360deg);
		}
	}

</style>
