<template>
	<svws-ui-content-card title="Daten">
		<div class="content-wrapper">
			<div class="input-wrapper">
				<svws-ui-multi-select title="Statistikkürzel" v-model="inputStatistikKuerzel" :items="inputKatalogReligionenStatistik"
					:item-text="(i: Religion) => i.daten.kuerzel" required />
				<svws-ui-text-input placeholder="Kürzel" v-model="inputKuerzel" type="text" />
				<svws-ui-text-input placeholder="Bezeichnung" v-model="inputText" type="text" />
				<svws-ui-text-input placeholder="Zeugnisbezeichnung" v-model="inputTextzeugnis" type="text" />
			</div>
		</div>
	</svws-ui-content-card>
</template>

<script setup lang="ts">

	import { computed, ComputedRef, WritableComputedRef } from "vue";
	import { Religion, ReligionEintrag} from "@svws-nrw/svws-core-ts";

	const props = defineProps<{
		data: ReligionEintrag;
	}>();

	const emit = defineEmits<{
		(e: 'patch', data: Partial<ReligionEintrag>): void;
	}>()

	function doPatch(data: Partial<ReligionEintrag>) {
		emit('patch', data);
	}

	const inputKatalogReligionenStatistik: ComputedRef<Religion[] | undefined> = computed(() => Religion.values());

	const inputKuerzel: WritableComputedRef<string | undefined> = computed({
		get: () => props.data.kuerzel ?? undefined,
		set: (value) => doPatch({ kuerzel: value })
	});

	const inputText: WritableComputedRef<string | undefined> = computed({
		get: () => props.data.text ?? undefined,
		set: (value) => doPatch({ text: value })
	});

	const inputTextzeugnis: WritableComputedRef<string | undefined> = computed({
		get: () => props.data.textZeugnis ?? undefined,
		set: (value) => doPatch({ textZeugnis: value })
	});

	const inputStatistikKuerzel: WritableComputedRef<Religion | undefined> = computed({
		get: () => Religion.getByKuerzel(props.data.kuerzel || null) || undefined,
		set: (value) => doPatch({ kuerzel: value?.daten.kuerzel || null })
	});

</script>
