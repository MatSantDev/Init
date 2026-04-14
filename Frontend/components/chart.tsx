"use client"

import { Pie, PieChart } from "recharts"

import {
  ChartConfig,
  ChartContainer,
  ChartLegend,
  ChartLegendContent,
  ChartTooltip,
  ChartTooltipContent,
} from "@/components/ui/chart"

const chartConfig = {
  quantidade: {
    label: "Quantidade",
    color: '#213',
  },
  pendente: {
    label: "Pendente",
    color: "#fff700",
  },
  concluido: {
    label: "Concluído",
    color: "#0fff73",
  },
  cancelado: {
    label: "Cancelado",
    color: "#ff0000",
  },
} satisfies ChartConfig

interface BudgetsStatusChartProps {
  data: {
    status: string;
    quantidade: number;
    fill: string;
  }[]
}

export function BudgetsStatusChart({ data }: BudgetsStatusChartProps) {
  return (
    <ChartContainer config={chartConfig} className="w-full">
      <PieChart>
        <ChartTooltip
          cursor={ false }
          content={ <ChartTooltipContent hideLabel /> }
        />
        <ChartLegend
          content={ <ChartLegendContent nameKey="status" /> }
          className="flex-wrap justify-center gap-4 mt-4"
        />
        <Pie
          data={ data }
          dataKey='quantidade'
          nameKey='status'
          innerRadius={ 75 }
          strokeWidth={ 5 }
        />
      </PieChart>
    </ChartContainer>
  )
}