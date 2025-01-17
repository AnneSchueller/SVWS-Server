<script lang="ts" setup>
	import { Size, ButtonType } from '../../types';

	const {
		type = 'primary',
		disabled = false,
		dropdownAction = false,
		size = 'normal'
	} = defineProps<{
		type?: ButtonType;
		disabled?: boolean;
		dropdownAction?: boolean;
		size?: Extract<Size, 'small' | 'normal' | 'big'>;
	}>();

	const emit = defineEmits<{
		(e: 'click', event: MouseEvent): void;
	}>();

	function onClick(event: MouseEvent) {
		if (!disabled) {
			emit("click", event);
		}
	}
</script>

<template>
	<button class="button" :class="{
		'button--primary': type === 'primary',
		'button--secondary': type === 'secondary',
		'button--danger': type === 'error' || type === 'danger',
		'button--transparent': type === 'transparent',
		'button--icon': type === 'icon',
		'button--trash': type === 'trash',
		'button--small': size === 'small',
		'button--big': size === 'big',
		'button--dropdown-action': dropdownAction === true,
	}" :disabled="disabled" @click="onClick">
		<slot v-if="type !== 'trash'" />
		<Icon v-if="type === 'trash'" class="button--trash-icon">
			<i-ri-delete-bin-line class="icon--line" />
			<i-ri-delete-bin-fill class="icon--fill" />
		</Icon>
	</button>
</template>

<style lang="postcss">
.button {
	@apply rounded-md border;
	@apply select-none;
	@apply text-button font-bold;
	@apply flex items-center;
	gap: 0.25em;
	padding: 0.45em 0.75em;

	svg {
		margin-top: -0.1em;
		margin-bottom: -0.1em;
	}

	&:hover {
		@apply brightness-110;
	}

	&:focus {
		@apply outline-none ring;
	}

	&:active {
		@apply ring-0 brightness-100;
	}
}

.button--primary {
	@apply bg-primary text-white border-primary;

	&:focus {
		@apply ring-primary ring-opacity-50;
	}
}

.button--secondary {
	@apply bg-transparent text-black border-dark;

	&:hover {
		@apply border-primary text-primary;
	}

	&:focus {
		@apply ring-primary ring-opacity-25 border-primary;
	}

	&:active {
		@apply bg-primary bg-opacity-5 brightness-100;
	}
}

.button--transparent {
	@apply bg-transparent border-transparent;

	&:hover {
		@apply bg-light brightness-95;
	}

	&:focus {
		@apply bg-light ring-dark-20 ring-opacity-75;
	}
}

.button--danger {
	@apply bg-transparent text-error border-error;

	&:hover,
	&:focus {
		@apply bg-error text-white;
	}

	&:focus {
		@apply ring-error ring-opacity-50;
	}
}

.button--trash {
	@apply bg-transparent rounded relative;
	@apply py-0 px-2;
	@apply text-error;
	border: 0 !important;
	padding: 0.2em !important;
	width: 1.6em;
	height: 1.6em;

	.icon--fill {
		@apply hidden;
	}

	&:hover {
		@apply bg-error text-white;

		.icon--line {
			@apply hidden;
		}

		.icon--fill {
			@apply inline-block;
		}
	}

	&:focus {
		@apply bg-error;
		@apply ring-error ring-opacity-25;
		@apply text-white;
	}
}

.button--icon {
	@apply p-2 justify-center border-0 items-center;
	@apply w-8 h-8;

	svg {
		width: 1.2rem;
		height: 1.2rem;
	}

	&:hover, &:focus {
		@apply bg-dark-20 bg-opacity-50 rounded;
	}

	&:focus {
		@apply ring-dark ring-opacity-50;
	}
}

.button:disabled {
	&,
	&:hover,
	&:focus {
		@apply bg-black bg-opacity-25 border-black border-opacity-50 text-black;
		@apply opacity-20;
		@apply cursor-not-allowed pointer-events-none;
	}
}

.button--dropdown-action {
	@apply relative z-20;
	@apply rounded-r-none;
	padding-right: 0.5em;
}

.hover--danger:hover {
	@apply text-error bg-error/10;
}

.button--small {
	@apply text-sm font-medium;
	padding: 0.3em 0.75em;
}

.button--big {
	padding-top: 0.64em;
	padding-bottom: 0.64em;
}
</style>
