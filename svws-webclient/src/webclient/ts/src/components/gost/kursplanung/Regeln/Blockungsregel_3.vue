<template>
	<BlockungsregelBase v-model="regel" @update:model-value="e => emit('update:modelValue', e)" :regel-typ="regel_typ" :regeln="regeln"
		@regel-hinzugefuegen="regel_hinzufuegen" @regel-speichern="emit('regelSpeichern')" @regel-entfernen="e=>emit('regelEntfernen', e)">
		<template #beschreibung="{ regel: r }">
			{{ getKursbezeichnung(getKursFromId(kurse, r.parameter.get(0)), mapFaecher) }} auf Schiene {{ r.parameter.get(1) }} gesperrt
		</template>
		Sperre
		<parameter-kurs v-model="kurs" :map-faecher="mapFaecher" :kurse="kurse" />
		in
		<parameter-schiene v-model="schiene" :schienen="schienen" />
	</BlockungsregelBase>
</template>

<script setup lang="ts">

	import { GostBlockungKurs, GostBlockungRegel, GostBlockungSchiene, GostFach, GostKursblockungRegelTyp } from "@svws-nrw/svws-core-ts";
	import { computed, WritableComputedRef } from "vue";
	import { getKursbezeichnung, useRegelParameterKurs, useRegelParameterSchiene, getKursFromId } from '../composables';

	const props = defineProps<{
		modelValue: GostBlockungRegel | undefined;
		mapFaecher: Map<number, GostFach>;
		kurse: GostBlockungKurs[];
		schienen: GostBlockungSchiene[];
		regeln: GostBlockungRegel[];
	}>();


	const emit = defineEmits<{
		(e: 'update:modelValue', v: GostBlockungRegel | undefined): void;
		(e: 'regelSpeichern'): void;
		(e: 'regelEntfernen', v: GostBlockungRegel): void;
	}>()

	const regel: WritableComputedRef<GostBlockungRegel | undefined> = computed({
		get: () => props.modelValue,
		set: (value) => emit('update:modelValue', value)
	});

	const regel_typ = GostKursblockungRegelTyp.KURS_SPERRE_IN_SCHIENE

	const kurs = useRegelParameterKurs(props.kurse, regel, 0)
	const schiene = useRegelParameterSchiene(props.schienen, regel, 1)

	const regel_hinzufuegen = (r: GostBlockungRegel) => {
		r.parameter.add(props.kurse[0].id)
		r.parameter.add(1)
		regel.value = r;
	}
</script>

