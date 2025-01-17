<script lang="ts" setup>
	type CheckboxValue = string | number | boolean | null;
	type ModelValue = boolean | Array<CheckboxValue> | undefined;

	const {
		value = '',
		statistics = false,
		disabled = false,
		circle = false,
		modelValue,
		headless = false
	} = defineProps<{
		value?: CheckboxValue;
		modelValue: ModelValue | null;
		statistics?: boolean;
		disabled?: boolean;
		circle?: boolean;
		headless?: boolean;
	}>();

	const emit = defineEmits<{
		(e: 'update:modelValue', event: ModelValue): void;
	}>();

	const model = computed({
		get(): ModelValue {
			return modelValue ?? undefined;
		},
		set(value: ModelValue) {
			emit("update:modelValue", value);
		}
	});
</script>

<template>
	<label class="checkbox"
		:class="{
			'checkbox--disabled': disabled,
			'checkbox--statistics': statistics,
			'checkbox--checked': modelValue,
			'checkbox--circle': circle,
			'checkbox--headless': headless,
			'checkbox--indeterminate': modelValue === undefined || modelValue === null
		}">
		<input v-model="model" class="checkbox--control" type="checkbox" :value="value" :disabled="disabled" :title="disabled ? 'Deaktiviert' : ''">
		<Icon v-if="modelValue === undefined">
			<i-ri-checkbox-indeterminate-line />
		</Icon>
		<Icon v-else-if="modelValue">
			<i-ri-checkbox-line v-if="!circle" />
			<i-ri-checkbox-circle-fill v-if="circle" />
		</Icon>
		<Icon v-else-if="!modelValue">
			<i-ri-checkbox-blank-line v-if="!circle" />
		</Icon>
		<span class="checkbox--label" v-if="$slots.default || statistics">
			<slot />
			<Icon v-if="statistics" class="ml-1">
				<i-ri-bar-chart-fill />
			</Icon>
		</span>
	</label>
</template>

<style>
.checkbox {
	@apply cursor-pointer;
	@apply inline-flex;
	@apply items-start justify-start;
	@apply select-none;
	@apply text-base font-normal leading-none;
	@apply my-1;
}

.checkbox:not(.checkbox--checked):not(.checkbox--indeterminate) .icon {
	@apply opacity-50;
}

.checkbox--control {
	@apply hidden;
}

.checkbox--disabled {
	@apply opacity-50;
	@apply cursor-not-allowed;
}

.checkbox--checked,
.checkbox--indeterminate {
	@apply font-medium;
}

.checkbox--label {
	margin: 0.1rem 0 0 0.2rem;
}

.checkbox--disabled .checkbox--label {
	@apply text-gray;
}
.checkbox--statistics {
	@apply text-purple;
}

.checkbox--statistics .checkbox--label .icon {
	@apply opacity-100 inline-block;
	width: 0.8em;
	height: 0.8em;

	svg {
		@apply w-full h-full;
	}
}

.checkbox--wrapper {
	margin-left: -1rem;
}

.checkbox--wrapper .checkbox {
	margin-left: 1rem;
}

.checkbox--headless {
	margin: 0;
}
</style>
