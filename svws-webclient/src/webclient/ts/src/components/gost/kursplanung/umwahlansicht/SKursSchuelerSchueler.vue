<template>
	<tr class="cursor-pointer" :class="{ 'selected': selected }">
		<td>
			<div class="flex justify-between items-center">
				<span>
					{{ `${schueler.nachname}, ${schueler.vorname}` }}
				</span>
				<div class="flex items-center">
					<svws-ui-badge v-if="schueler.status !== 'Aktiv'" type="light" size="big" class="mr-1"> {{ schueler.status }} </svws-ui-badge>
					<div class="leading-none overflow-hidden w-5"
						style="margin-bottom: -0.1em;"
						:class="{
							'color--error': kollision,
							'color--dark': !kollision,
						}">
						<svws-ui-popover v-if="kollision && !nichtwahl" class="popper--dark">
							<template #trigger>
								<svws-ui-icon> <i-ri-alert-line /> </svws-ui-icon>
							</template>
							<template #content>
								<span>Kollision</span>
							</template>
						</svws-ui-popover>
						<svws-ui-popover v-else-if="!kollision && nichtwahl" class="popper--dark">
							<template #trigger>
								<svws-ui-icon> <i-ri-forbid-2-line /> </svws-ui-icon>
							</template>
							<template #content>
								<span>Nichtwahl</span>
							</template>
						</svws-ui-popover>
						<svws-ui-popover v-else-if="kollision && nichtwahl" class="popper--danger">
							<template #trigger>
								<svws-ui-icon> <i-ri-alert-fill /> </svws-ui-icon>
							</template>
							<template #content>
								<span>Kollision und Nichtwahl</span>
							</template>
						</svws-ui-popover>
						<svws-ui-icon v-else class="opacity-25"> <i-ri-check-fill /> </svws-ui-icon>
					</div>
				</div>
			</div>
		</td>
	</tr>
</template>

<script setup lang="ts">

	import { GostBlockungsergebnisManager, SchuelerListeEintrag } from "@svws-nrw/svws-core-ts";
	import { ComputedRef, computed } from "vue";
	import { GostKursplanungSchuelerFilter } from "../GostKursplanungSchuelerFilter";

	const props = defineProps<{
		getErgebnismanager: () => GostBlockungsergebnisManager;
		schueler: SchuelerListeEintrag;
		schuelerFilter: GostKursplanungSchuelerFilter;
		selected: boolean;
	}>();

	const kollision: ComputedRef<boolean> = computed(()=> {
		const kursid = props.schuelerFilter.kurs.value?.id;
		if (kursid === undefined)
			return props.getErgebnismanager().getOfSchuelerHatKollision(props.schueler.id);
		return props.getErgebnismanager().getOfKursSchuelermengeMitKollisionen(kursid).contains(props.schueler.id);
	});

	const nichtwahl: ComputedRef<boolean> = computed(() => props.getErgebnismanager().getOfSchuelerHatNichtwahl(props.schueler.id));

</script>
