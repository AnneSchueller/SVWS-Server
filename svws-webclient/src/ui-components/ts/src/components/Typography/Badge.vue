<script setup lang='ts'>
	import { Size, Type } from '~/types';

	const {
		type = 'light',
		size = 'normal',
	} = defineProps<{
		type?: Type;
		size?: Size;
	}>();
</script>

<template>
	<span class="badge" :class="{
		'badge--primary': type === 'primary',
		'badge--success': type === 'success',
		'badge--error': type === 'error',
		'badge--highlight': type === 'highlight',
		'badge--light': type === 'light',
		'badge--lg': size === 'big',
		'badge--normal': size === 'normal',
	}">
		<slot />
	</span>
</template>

<style lang="postcss">
.badge {
	@apply font-bold leading-none;
	@apply rounded-full inline-flex items-center align-text-top;
	font-size: 0.65em;
	padding: 0.35em 0.5em;
	gap: 0.25em;

	svg {
		margin: -0.1em;
	}
}

.badge--lg {
	font-size: 0.8em;
	padding: 0.25em 0.45em;
}

.badge--error {
	@apply bg-error;
	@apply border-error;
	@apply text-white;
}

.badge--primary {
	@apply bg-primary;
	@apply border-primary;
	@apply text-white;
}

.badge--light {
	@apply bg-light;
	@apply border-light;
	@apply text-dark;
}

.badge--success {
	@apply bg-success;
	@apply border-success;
	@apply text-dark;
}

.badge--highlight {
	@apply bg-highlight;
	@apply border-highlight;
	@apply text-dark;
}
</style>
