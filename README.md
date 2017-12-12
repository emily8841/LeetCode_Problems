# Slurm Workload Manager

Slurm is an open source, fault-tolerant, and highly scalable cluster management
and job scheduling system for large and small Linux clusters. Slurm requires no
kernel modifications for its operation and is relatively self-contained. As a
cluster workload manager, Slurm has three key functions. First, it allocates
exclusive and/or non-exclusive access to resources (compute nodes) to users for
some duration of time so they can perform work. Second, it provides a framework
for starting, executing, and monitoring work (normally a parallel job) on the
set of allocated nodes. Finally, it arbitrates contention for resources by
managing a queue of pending work. Optional plugins can be used for accounting,
advanced reservation, gang scheduling (time sharing for parallel jobs),
backfill scheduling, topology optimized resource selection, resource limits by
user or bank account, and sophisticated multifactor job prioritization algorithms.
See "https://slurm.schedmd.com/overview.html" for more information.

## To Use

To deploy, you must have a GCP account and have gcloud installed.

1.  Edit the `slurm-cluster.yaml` file and specify the required values

    For example:

    ```
    resources:
    - name: slurm-cluster
      type: slurm.jinja
      properties:
        static_node_count       : 2
        max_node_count          : 100
        zone                    : us-west1-a
        controller_machine_type : n1-standard-4
        compute_machine_type    : n1-standard-4
        login_machine_type      : n1-standard-1
        slurm_version           : 17.02.9
        default_account         : default
        default_users           : brian,joe
        munge_key               : vkes5gHJtx9SO0sHI5fV5jKBGAIxA1q7LogLUGtzVQE
    ```

2.  Spin up the cluster.

    Assuming that you have gcloud configured for your account, you can just run:

    ```
    $ gcloud deployment-manager deployments create slurm --config slurm-cluster.yaml
    ```

3.  Check the cluster status.

    To verify that the deployment scripts all worked, ssh to the login node and
    run `sinfo` to see how many nodes are registered and in an IDLE state.

    ```
    $ gcloud compute ssh login1
    ...
    [brian@login1 ~]$ sinfo
    PARTITION AVAIL  TIMELIMIT  NODES  STATE NODELIST
    debug*       up   infinite      2   idle compute[1-2]
    ```

4.  Submit jobs on the cluster.

    ```
   [brian@login1 ~]$ sbatch -N2 --wrap="srun hostname"
   Submitted batch job 2
   [brian@login1 ~]$ cat slurm-2.out
   compute1
   compute2

   ```

5. Tearing down the employment.

   ```
   $ gcloud deployment-manager deployments delete slurm
   ```

