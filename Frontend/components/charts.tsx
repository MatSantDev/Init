 'use client'

import {
  Bar,
  BarChart,
  CartesianGrid,
  LabelList,
  Pie,
  PieChart,
  XAxis,
  YAxis,
 } from 'recharts'

import {
  ChartConfig,
  ChartContainer,
  ChartTooltip,
  ChartTooltipContent,
  ChartLegend,
  ChartLegendContent,
} from '@/components/ui/chart'

const statusPieChartConfig = {
  concluido: {
    label: 'Concluido',
    color: '#0466c8',
  },
  pendente: {
    label: 'Pendente',
    color: '#0353a4',
  },
  cancelado: {
    label: 'Cancelado',
    color: '#023e7d',
  },
} satisfies ChartConfig

const clientsBudgetsBarChartConfig = {
  quantidade: {
    label: 'Orçamentos',
    color: '#3b82f6',
  },
} satisfies ChartConfig

interface BudgetsStatusPieChartProps {
  data: {
    status: string;
    quantidade: number;
    fill: string;
  }[]
}

interface ClientsBudgetsChartProps {
  data: {
    nome: string;
    quantidade: number;
  }[]
}

export function BudgetsStatusPieChart( { data }: BudgetsStatusPieChartProps ) {
  return (
    <ChartContainer
      config={ statusPieChartConfig }
      className='h-96 max-h-3/4 flex mx-auto aspect-square'
    >
      <PieChart>
        <ChartTooltip
          cursor={ false }
          content={<ChartTooltipContent hideLabel />}
        />
        
        <Pie
          data={ data }
          dataKey='quantidade'
          nameKey='status'
          innerRadius={ 60 }
          strokeWidth={ 5 }
        />

        <ChartLegend
          content={ <ChartLegendContent nameKey='status' />}
          className=' justify-center gap-4 mt-4'
        />

      </PieChart>
    </ChartContainer>
  )
}

export function ClientsBudgetsChart( { data }: ClientsBudgetsChartProps ) {
  return (
    <ChartContainer
      config={ clientsBudgetsBarChartConfig }
      className='h-96 max-h-3/4 w-full'
    >
      <BarChart
        accessibilityLayer
        data={ data }
        barCategoryGap='20%'
        margin={ { top: 30, right: 10, left: 10, bottom: 10 } }
      >
        <CartesianGrid
          vertical={ false }
        />

        <XAxis
          dataKey='nome'
          tickLine={ false }
          tickMargin={ 10 }
          axisLine={ false }
          tickFormatter={ ( value ) =>
            value.length > 12 ? value.slice( 0, 12 ) + '...' : value 
          }
        />

        <YAxis
          tickLine={ false }
          axisLine={ false }
          tickMargin={ 10 }
          tickCount={ 5 }
          allowDecimals={ false }
        />

        <ChartTooltip cursor={ false } content={ <ChartTooltipContent hideLabel /> } />

        <Bar
          dataKey='quantidade'
          fill='var(--color-quantidade)'
          radius={ [ 6, 6, 0, 0 ] }
          // barSize={ 40 }
        >
          <LabelList
            dataKey='quantidade'
            position='top'
            offset={ 12 }
            className='fill-foreground font-medium text-xs'
          />
        </Bar>

      </BarChart>
    </ChartContainer>
  )
}