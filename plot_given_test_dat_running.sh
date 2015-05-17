#!/usr/bin/gnuplot

set terminal pngcairo enhanced font 'Verdana,12'
set title "Running time on the given testdata"
set output 'combined-given-data-running-time.png'

set xlabel "input size"
set ylabel "running time per operation (ms)"
set xtics autofreq
set grid ytics lc rgb "#bbbbbb" lw 1 lt 0
set grid xtics lc rgb "#bbbbbb" lw 1 lt 0

set xr [0:1100]
set yr [0:50]
set key right top

f(x) = a*x**3
g(x) = (m*x**2)*(log(x)/log(2))
fit f(x) 'data/floyd_test.dat' using ($1):($2 / (3*($1**2))) via a
fit g(x) 'data/sankowski_test.dat' using ($1):($2 / (3*($1**2))) via m

plot "data/floyd_test.dat" using 1:($2 / (3*($1**2))) title 'Floyd-Warshall' pointtype 5,\
     "data/sankowski_test.dat" using 1:($2 / (3*($1**2))) title 'Sankowski' pointtype 7,\
     f(x) with lines title sprintf("Floyd-fit %.3Ex^{%.1f}", a,3,0),\
     g(x) with lines title sprintf("Sankowski-fit %.3Ex^{%.1f} log_2(x)", m,2)



