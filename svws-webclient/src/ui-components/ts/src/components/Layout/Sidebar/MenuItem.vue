<script setup lang='ts'>
	const {
		active = false,
		collapsed = false,
		subline = '',
	} = defineProps<{
		active?: boolean;
		collapsed?: boolean;
		subline?: string;
	}>();

	const emit = defineEmits<{
		(e: 'click', event: MouseEvent): void;
	}>();

	function onClick(event: MouseEvent) {
		emit("click", event);
	}
</script>

<template>
	<a class="sidebar--menu-item" :class="{
		'sidebar--menu-item--active': active,
		'sidebar--menu-item--collapsed': collapsed
	}" href="#" @click.prevent="onClick"
		:title="($slots.label?.()[0].children as unknown as string)">
		<span v-if="$slots.icon" class="sidebar--menu-item--icon">
			<Icon>
				<slot name="icon" />
			</Icon>
		</span>
		<span class="sidebar--menu-item--label">
			<slot name="label" />
			<span v-if="subline" class="sidebar--menu-item--subline">
				{{ subline }}
			</span>
		</span>
	</a>
</template>

<style lang="postcss">
.sidebar--menu-item {
	@apply flex items-center flex-col;
	@apply p-2 rounded my-1;
	@apply cursor-pointer;
	padding: 0.25rem 0.1rem;
}

.sidebar--menu-item:last-child {
	@apply mb-0;
}

.sidebar--menu--body .sidebar--menu-item--active,
.sidebar--menu--footer .sidebar--menu-item--active {
	@apply bg-dark-20;
}

.sidebar--menu-item:hover,
.sidebar--menu-item:focus,
.sidebar--menu--body .sidebar--menu-item:hover,
.sidebar--menu--body .sidebar--menu-item:focus,
.sidebar--menu--footer .sidebar--menu-item:hover,
.sidebar--menu--footer .sidebar--menu-item:focus {
	@apply text-primary bg-primary bg-opacity-10;
}

.sidebar--menu-item--label {
	@apply text-ellipsis overflow-hidden;
	max-width: 100%;
	display: -webkit-box;
	-webkit-box-orient: vertical;
	-webkit-line-clamp: 1;
	word-break: break-all;
	font-size: 0.78rem;
}

@media (min-width: 1280px) {
	.sidebar--menu .sidebar--menu-item--label {
		font-size: 0.9rem;
	}
}

.secondary-menu--content .sidebar--menu-item {
	@apply text-dark px-3 inline-block my-1;
}

.secondary-menu--content .sidebar--menu-item--label {
	@apply text-base;
}

.secondary-menu--content .sidebar--menu-item:hover,
.secondary-menu--content .sidebar--menu-item:focus {
	@apply text-primary bg-primary bg-opacity-10;
}

.secondary-menu--content .sidebar--menu-item--active {
	@apply border-dark;
	@apply font-bold;
}

.sidebar--menu-item--icon {
	@apply mr-0 mt-1;
	font-size: 1.2rem;
}

@media (min-width: 1280px) {
	.sidebar--menu-item--icon {
		font-size: 1.4rem;
	}
}

@media (min-width: 2000px) {
	.sidebar--menu-item--icon {
		@apply mb-1;
		font-size: 1.5rem;
	}
}

.sidebar--menu-item--subline {
	@apply text-caption font-normal;
}
</style>
