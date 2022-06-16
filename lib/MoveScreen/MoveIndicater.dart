import 'dart:ui';
import 'package:flutter/material.dart';
import 'package:uberkabahle/MoveScreen/AlgoritmController/SuggestedMove.dart';

class MoveIndicator extends StatelessWidget {
  final SuggestedMove suggestedMove;
  final VoidCallback nextMoveHandler;
  const MoveIndicator({required this.suggestedMove, required this.nextMoveHandler, Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return BackdropFilter(
      filter: ImageFilter.blur(sigmaX: 3, sigmaY: 3),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: (suggestedMove.flipStack)
                ? [
                    SizedBox(
                      width: 150,
                      child: Column(
                        children: [
                          Row(
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: [
                              Image.asset(
                                "assets/cards/drawFromPile.png",
                              ),
                              IconButton(
                                onPressed: nextMoveHandler,
                                icon: const Icon(
                                  Icons.arrow_right_alt_rounded,
                                  size: 80,
                                ),
                              ),
                            ],
                          ),
                        ],
                      ),
                    ),
                  ]
                : [
                    SizedBox(
                      width: 150,
                      child: Column(
                        children: [
                          Image.asset(
                            "assets/cards/${suggestedMove.moveCard.label}.png",
                          ),
                          const SizedBox(
                            height: 20,
                          ),
                          Text(
                            "Tableau ${suggestedMove.fromColumn + 1}",
                            style: const TextStyle(fontSize: 22, fontWeight: FontWeight.bold),
                          ),
                        ],
                      ),
                    ),
                    const SizedBox(width: 30),
                    GestureDetector(
                      onTap: nextMoveHandler,
                      child: const Icon(
                        Icons.arrow_right_alt_rounded,
                        size: 80,
                      ),
                    ),
                    const SizedBox(width: 50),
                    SizedBox(
                      width: 150,
                      child: Column(
                        children: [
                          Image.asset(
                            "assets/cards/${suggestedMove.toCard.label}.png",
                          ),
                          const SizedBox(
                            height: 20,
                          ),
                          Text(
                            "Tableau ${suggestedMove.toColumn + 1}",
                            style: const TextStyle(fontSize: 22, fontWeight: FontWeight.bold),
                          ),
                        ],
                      ),
                    ),
                  ],
          ),
        ],
      ),
    );

    // // For placing on top of relevant pile:
    // // *************************************
    // return Positioned(
    //   left: (moveCard) ? 100 : 300,
    //   top: 200,
    //   width: 150,

    //   // left: card.renderLocation.left,
    //   // top: card.renderLocation.top,
    //   // width: card.renderLocation.width,
    //   // height: card.renderLocation.height,
    //   child: Column(
    //     children: [
    //       Image.asset(
    //         "assets/cards/${card.label}.png",
    //       ),
    //       Text(
    //         "Column $columnIndex",
    //         style: TextStyle(fontSize: 20),
    //       ),
    //     ],
    //   ),
    // );
  }
}
