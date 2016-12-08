# Concurrent Programming, Project 1

`MojaPlansza` is a synchronized implementation of the `Plansza` interface,
allowing for synchronized placement, movement, and removal of `Postać` objects,
as well as synchronized performance of `Akcja` actions on `Postać` objects on
the board.

## Testing

Unit tests for each helper object are found in `testy/<object name>Tests.java`.
You can run all of them with `make run_tests`.

`testy/GraWŻycie` shows an implementation of Conway's Game of Life using
`Plansza` and `Postać` objects.
