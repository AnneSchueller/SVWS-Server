<template>
	<div>
		<svws-ui-button type="transparent" size="small" @click="modal.openModal()" class="print:hidden">
			<span>Hilfe</span>
			<i-ri-question-line />
		</svws-ui-button>
		<svws-ui-modal ref="modal" size="help">
			<template #modalTitle>
				<div class="inline-flex items-center gap-1">
					Hilfe <i-ri-question-line />
				</div>
			</template>
			<template #modalDescription>
				<slot>Für diesen Bereich wurde noch kein Text geschrieben.</slot>
			</template>
		</svws-ui-modal>
	</div>
</template>

<script setup lang="ts">

	import { ref } from 'vue';

	const modal = ref();

</script>
