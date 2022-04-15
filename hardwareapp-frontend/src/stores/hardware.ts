import type { HardwareDTO } from "@/models/hardware";
import { getAllHardware } from "@/services/hardware";
import { set } from "@vueuse/core";

export const useHardwareStore = defineStore("hardware", () => {
  const hardware = ref<HardwareDTO[]>([]);

  const fetchHardware = async () => {
    try {
      const data = await getAllHardware();
      if (data) {
        set(hardware, data);
        return hardware.value;
      }
      return null;
    } catch (err: any) {
      console.error(err.message, err);
      return null;
    }
  };

  return {
    hardware,
    fetchHardware,
  };
});

if (import.meta.hot)
  import.meta.hot.accept(acceptHMRUpdate(useHardwareStore, import.meta.hot));
