<script setup lang='ts'>
	const {
		collapsed = false,
	} = defineProps<{
		collapsed?: boolean;
	}>();

	const emit = defineEmits<{
		(e: 'click', event: MouseEvent): void;
	}>();

	function onClick(event: MouseEvent) {
		emit("click", event);
	}
</script>

<template>
	<a class="sidebar--menu--initials"
		href="#" @click.prevent="onClick">
		<Popover>
			<template #trigger>
				<div class="sidebar--menu-header--icon">
					A
				</div>
			</template>
			<template #content>
				<div class="sidebar--menu-header--label">
					Angemeldet als <slot />
				</div>
			</template>
		</Popover>
	</a>
</template>

<style>
.sidebar--menu--initials {
	@apply flex flex-col items-center w-full justify-center mx-auto relative;
}

.sidebar--menu--initials .sidebar--menu-header--icon {
	@apply flex flex-col items-center w-full justify-center mx-auto relative;
	@apply rounded-full overflow-hidden bg-dark-80 text-white;
	@apply w-10 xl:w-12 h-10 xl:h-12;
}

.sidebar--menu--initials svg {
	@apply w-3/4 mx-auto hidden;
}
</style>
