#!/usr/bin/env python3
import re
import sys


class Transaction(object):
    def __init__(self, from_state, action, to_state):
        self.from_state = from_state
        self.action = action
        self.to_state = to_state
        self.count = 1

    def __str__(self):
        return "%s -- %s -> %s" % (self.from_state, self.action, self.to_state)


if __name__ == '__main__':
    if len(sys.argv) < 2:
        print("Missing parameter of log file!")
        exit(-1)

    regex = re.compile(r'^(.*)\((.*)\)$')


    def parse_arg(_str):
        match = regex.match(_str)
        if match:
            return match.group(1), match.group(2)
        return _str, None


    current_state = 'Start'
    fsm = {'Start': {}}

    with open(sys.argv[1]) as f:
        for line in f:
            line = line.strip()
            sp = line.split('->')
            tx, tx_arg = parse_arg(sp[0].strip())
            state, state_arg = parse_arg(sp[1].strip())

            if tx == 'Click':
                action = '%s %s' % (tx, tx_arg)
            elif tx == 'Select' or tx == 'Deselect':
                action = 'Select / Deselect'
            else:
                action = tx

            if current_state not in fsm:
                fsm[current_state] = {}

            transactions = fsm[current_state]
            if action not in transactions:
                transactions[action] = Transaction(current_state, action, state)
            else:
                transaction = transactions[action]  # type: Transaction
                # make sure it's the same transaction
                if transaction.to_state != state:
                    print('Transaction inconsistent! Last was %s, now is %s -- %s --> %s.' % (
                        transaction, current_state, action, tx))
                    exit(-2)
                transaction.count += 1

            current_state = state

    # print result
    for _, txs in fsm.items():
        total_out_txs = 0
        for _, tx in txs.items():  # type: Transaction
            total_out_txs += tx.count

        for _, tx in txs.items():  # type: Transaction
            print('Transaction [%s] probability: %s' % (tx, tx.count / total_out_txs))
