<template>
	<svws-ui-modal ref="kurs_und_kurs_modal" size="small">
		<template #modalTitle>Regel erstellen für Kurse</template>
		<template #modalDescription>
			<div class="">
				Sollen die Kurse {{ props.kurs1Id === undefined ? '???' : getDatenmanager().getNameOfKurs(props.kurs1Id) }} und {{ getDatenmanager().getNameOfKurs(props.kurs2Id) }} immer oder nie zusammen auf einer Schiene liegen?
			</div>
			<div class="flex gap-1">
				<svws-ui-button @click="close">Abbrechen</svws-ui-button>
				<svws-ui-button @click="regelImmerZusammen">Immer</svws-ui-button>
				<svws-ui-button @click="regelNieZusammen">Nie</svws-ui-button>
			</div>
		</template>
	</svws-ui-modal>
</template>

<script setup lang="ts">

	import { GostBlockungRegel, GostBlockungsdatenManager, GostKursblockungRegelTyp } from '@svws-nrw/svws-core-ts';
	import { Ref, ref, watch } from 'vue';

	const props = defineProps<{
		getDatenmanager: () => GostBlockungsdatenManager;
		addRegel: (regel: GostBlockungRegel) => Promise<GostBlockungRegel | undefined>;
		modelValue: boolean;
		kurs1Id?: number;
		kurs2Id: number;
	}>();

	const emit = defineEmits<{
		(e: 'update:modelValue', v: boolean): void;
	}>()

	const kurs_und_kurs_modal: Ref<any> = ref(null);

	watch(() => props.modelValue, (newValue : boolean, oldValue : boolean) => {
		if (newValue !== oldValue) {
			if (newValue)
				kurs_und_kurs_modal.value.openModal();
			else
				kurs_und_kurs_modal.value.closeModal();
		}
	});

	function close() {
		emit('update:modelValue', false);
	}

	async function regelImmerZusammen() {
		if (props.kurs1Id === undefined)
			return;
		const regel = new GostBlockungRegel();
		regel.typ = GostKursblockungRegelTyp.KURS_ZUSAMMEN_MIT_KURS.typ;
		regel.parameter.add(props.kurs1Id)
		regel.parameter.add(props.kurs2Id);
		await props.addRegel(regel);
		close();
	}

	async function regelNieZusammen() {
		if (props.kurs1Id === undefined)
			return;
		const regel = new GostBlockungRegel();
		regel.typ = GostKursblockungRegelTyp.KURS_VERBIETEN_MIT_KURS.typ;
		regel.parameter.add(props.kurs1Id)
		regel.parameter.add(props.kurs2Id);
		await props.addRegel(regel);
		close();
	}

</script>
